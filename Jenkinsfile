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
                sh 'curl -s http://my-java-app-container:8081/message | grep "Hello, Database!"'
                sh '''
                    curl -s -X POST http://my-java-app-container:8081/message \
                    -H "Content-Type: application/json" \
                    -d \'{"id":2,"content":"Test message"}\' | grep "Message saved: Test message"
                '''
            }
        }
    }
    post {
        failure {
            sh 'docker-compose down || true'
        }
    }
}
