def call(String imageName) {
    def measureExecutionTime(Closure closure) {
        def startTime = System.currentTimeMillis()
        closure.call()
        def endTime = System.currentTimeMillis()
        def elapsedTime = endTime - startTime
        println "Time taken: ${elapsedTime} milliseconds"
    }
    pipeline {
        agent any

        stages {
            stage('Pull Docker Image') {
                steps {
                    script {
                        measureExecutionTime{
                            sh "docker pull ${imageName}"
                        }
                    }
                }
            }

            stage('Run Docker Container') {
                steps {
                    script {
                        def containerName = 'your-container-name'
                        sh "docker stop ${containerName} || true"
                        sh "docker rm ${containerName} || true"

                        def dockerCmd = "docker run -d -p 80:80 --name ${containerName} ${imageName}"
                        sh dockerCmd

                        sh "docker logs ${containerName}"
                    }
                }
            }

            stage('Show Running Containers') {
                steps {
                    sh 'docker ps -a'
                }
            }
        }
    }
}
