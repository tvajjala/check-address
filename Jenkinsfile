pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }

         stage('E2E') {
                    steps {
                        echo 'E2E Testing..'
                    }
         }

        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}