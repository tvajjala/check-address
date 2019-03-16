
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