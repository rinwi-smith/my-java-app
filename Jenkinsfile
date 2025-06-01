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
                sh 'docker network inspect my-java-app_java-network || true'
            }
        }
        stage('Run Unit Tests') {
            steps {
                sh 'docker-compose down || true'
                sh 'docker-compose up -d --build'
                sh 'docker exec my-java-app-container mvn test'
            }
        }
        stage('Test App') {
            steps {
                sh 'sleep 60'
                sh 'curl -s http://my-java-app-container:8081 | grep "Hello, Java!"'
                sh 'curl -s http://my-java-app-container:8081/message | grep "Hello, Database!"'
                sh '''curl -s -X POST http://my-java-app-container:8081/message \
                      -H "Content-Type: application/json" \
                      -d '{"id":2,"content":"New Message"}' | grep "Message saved: New Message"'''
                sh 'curl -s http://my-java-app-container:8081/messages | grep "Hello, Database!"'
                sh '''curl -s -X DELETE http://my-java-app-container:8081/message/2 | grep "Message with ID 2 deleted"'''
            }
        }
    }
}