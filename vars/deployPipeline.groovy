def call(String imageName) {
    pipeline {
        agent any

        stages {
            stage('Pull Docker Image') {
                steps {
                    script {
                        sh "docker pull ${imageName}"
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
