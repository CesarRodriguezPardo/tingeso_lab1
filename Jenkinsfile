pipeline{
    agent any
    tools{
        maven "maven"

    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/CesarRodriguezPardo/tingeso_lab1']])
                dir("back"){
                    sh "mvn clean install"
                }
            }
        }
        stage("Test"){
            steps{
                dir("back"){
                    sh "mvn test"
                }
            }
        }        
        stage("Build and Push Docker Image"){
            steps{
                dir("back"){
                    script{
                         withDockerRegistry(credentialsId: 'docker-credentials'){
                            sh "docker build --platform=linux/amd64 -t cesarrodriguezpardo/prestabanco-backend ."
                            sh "docker push cesarrodriguezpardo/prestabanco-backend"
                        }
                    }                    
                }
            }
        }
    }
}