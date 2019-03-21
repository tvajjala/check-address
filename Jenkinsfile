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

        stage('Tests') {

            parallel {

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
            }
        }


        stage('prepare docker image') {
            steps {
               echo 'prepare docker image ...'
            }
        }


        stage('contractTest') {
            steps {
                sh  './gradlew contractTest'
                echo 'Running contractTest ..'
            }
        }


        stage('integrationTest') {
            steps {
                sh  './gradlew functionalTest'
                echo 'Running functionalTest ..'
            }
        }


    }
}