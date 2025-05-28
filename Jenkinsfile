pipeline {
    agent any
    stages {
        stage('Clean Up') {
            steps {
                sh 'docker-compose down || true'
            }
        }
        stage('Build and Run with Docker Compose') {
            steps {
                sh 'docker-compose up -d --build'
            }
        }
        stage('Test App') {
            steps {
                sh 'sleep 10'
                sh 'curl -s http://my-java-app-container:8081 | grep "Hello, Java!"'
            }
        }
    }
    // post {
    //     failure {
    //         sh 'docker-compose down || true'
    //     }
    // }
}
