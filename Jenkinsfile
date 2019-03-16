
pipeline {
    agent any

    stages {

        stage('Checkout'){
            steps{
                checkout scm
            }
        }


        stage('Build') {
            steps {
                sh  './gradlew clean build'
            }
        }

        stage('Code Quality'){
            parallel{
                stage('Sonar'){
                    echo 'Running Sonar report'
                }
                stage('Klockwork'){
                    echo 'Running Klockwork report'
                }

                stage('Veracode'){
                    echo 'Running Veracode report'
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



        stage('Docker image') {

            steps {
               echo 'Dockerize ...'
            }
        }


        stage('Deploy') {

            parallel{

                stage('DEV1'){
                    echo 'Deploying to dev1'
                }
                stage('DEV2'){
                    echo 'Deploying to dev2'
                }

                stage('DEV3'){
                    echo 'Deploying to dev3'
                }
            }
        }


        stage('ContractTest') {
            steps {
                 sh  './gradlew contractTest'
                 echo 'Running contractTest ..'
            }
        }


        stage('functionalTest') {
            steps {
                 sh  './gradlew functionalTest'
                 echo 'Running functionalTest ..'
            }
        }



    }
}