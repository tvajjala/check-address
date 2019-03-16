
pipeline {
    agent any

    stages {

        stage('Checkout'){
            checkout scm
        }


        stage('Build') {
            steps {
                echo 'Building..'
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