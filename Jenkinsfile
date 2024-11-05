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
                    bat "mvn clean install"
                }
            }
        }
        stage("Test"){
            steps{
                dir("back"){
                    bat "mvn test"
                }
            }
        }        
        stage("Build and Push Docker Image"){
            steps{
                dir("back"){
                    script{
                         withDockerRegistry(credentialsId: 'docker-credentials'){
                            bat "docker build -t cesarrodriguezpardo/prestabanco-backend ."
                            bat "docker push cesarrodriguezpardo/prestabanco-backend"
                        }
                    }                    
                }
            }
        }
    }
}