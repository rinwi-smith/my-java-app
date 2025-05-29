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
        stage('Debug Info') {
            steps {
                sh 'docker ps -a || true'
                sh 'docker logs my-java-app-container || true'
                sh 'docker logs my-db-container || true'
                sh 'docker network ls || true'
                sh 'docker network inspect my-java-app-pipeline_java-network || true'
            }
        }
        stage('Test App') {
            steps {
                sh 'sleep 60'
                sh 'curl -v http://my-java-app-container:8081 || echo "Curl failed"'
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
