def measureExecutionTime(Closure closure) {
    def startTime = System.currentTimeMillis()
    closure.call()
    def endTime = System.currentTimeMillis()
    def elapsedTime = endTime - startTime
    return elapsedTime
}

def call(String imageName) {
    def timings = []

    pipeline {
        agent any
        stages {
            stage('Pull Docker Image') {
                steps {
                    script {
                        def elapsedTime = measureExecutionTime {
                            sh "docker pull ${imageName}"
                        }
                        timings << [stage: 'Pull Docker Image', time: elapsedTime]
                    }
                }
            }

            stage('Run Docker Container') {
                steps {
                    script {
                        def containerName = 'your-container-name'
                        def elapsedTime = measureExecutionTime {
                            sh "docker stop ${containerName} || true"
                            sh "docker rm ${containerName} || true"

                            def dockerCmd = "docker run -d -p 80:80 --name ${containerName} ${imageName}"
                            sh dockerCmd

                            sh "docker logs ${containerName}"
                        }
                        timings << [stage: 'Run Docker Container', time: elapsedTime]
                    }
                }
            }

            stage('Show Running Containers') {
                steps {
                    script {
                        def elapsedTime = measureExecutionTime {
                            sh 'docker ps -a'
                        }
                        timings << [stage: 'Show Running Containers', time: elapsedTime]
                         def elapsedTime1 = measureExecutionTime {
                            sh 'docker pull tomcat'
                        }
                        timings << [stage: 'Show Tomcat Pull', time: elapsedTime1]
                    }
                }
            }

            stage('Print Timings') {
                steps {
                    script {
                        def table = 'Stage\t\t\tTime (ms)\n'
                        table += '--------------------------------\n'
                        timings.each { timing ->
                            table += "${timing.stage}\t\t${timing.time}\n"
                        }
                        println table
                    }
                }
            }
        }
    }
}
