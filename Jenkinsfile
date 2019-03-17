pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps{
                checkout scm
            }
        }


        stage('Build') {
            steps {
                sh  './gradlew clean build'
            }
        }

        stage('Code Quality') {

            parallel {
                stage('Sonar') {
                    steps {
                        echo 'Running Sonar report'
                    }
                }
                stage('Klockwork') {
                    steps {
                        echo 'Running Klockwork report'
                    }
                }
                stage('Veracode') {
                    steps {
                         echo 'Running Veracode report'
                    }
                }
            }
        }


        stage('componentTest') {
            steps {
              sh  './gradlew componentTest'
              echo 'Running componentTest ..'
            }
        }


        stage('layerTest') {
            steps {
              sh  './gradlew layerTest'
              echo 'Running layerTest ..'
            }
        }



        stage('prepare docker image') {
            steps {
               echo 'prepare docker image ...'
            }
        }


        stage('Deploy') {
            parallel {
                stage('deploying to DEV1 server') {
                    steps {
                         echo 'deploying to DEV1 server'
                    }
                }
                stage('deploying to DEV2 server') {
                    steps {
                         echo 'deploying to DEV2 server'
                    }
                }
                stage('deploying to DEV3 server') {
                    steps {
                         echo 'deploying to DEV3 server'
                    }
                }
            }
        }


        stage('contractTest') {
            steps {
                 sh  './gradlew contractTest'
                 echo 'Running contractTest ..'
            }
        }

        stage('Generate contracts') {
            steps {
                echo 'Publishing contracts ..'
            }
        }

        stage('functionalTest') {
            steps {
                 sh  './gradlew functionalTest'
                 echo 'Running functionalTest ..'
            }
        }

        stage('Check Health') {
            steps {
                echo 'Checking health..'
            }
        }
    }
}