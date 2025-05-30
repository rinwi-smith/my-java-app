pipeline {
    agent any
    stages {
        stage('Clean Up') {
            steps {
                sh '''
                    docker-compose down || true
                    docker rm -f my-java-app-container my-db-container || true
                    docker network rm my-java-app-pipeline_java-network || true
                '''
            }
        }
        stage('Build and Run with Docker Compose') {
            steps {
                sh '''
                    docker network create my-java-app-pipeline_java-network || true
                    docker-compose up -d --build
                '''
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
                sh 'curl -s http://my-java-app-container:8081 | grep "Hello, Java!"'
                sh 'curl -s http://my-java-app-container:8081/message | grep "Hello, Database!"'
                sh '''curl -s -X POST http://my-java-app-container:8081/message \
                      -H "Content-Type: application/json" \
                      -d '{"id":2,"content":"New Message"}' | grep "Message saved: New Message"'''
                sh 'curl -s http://my-java-app-container:8081/messages | grep "Hello, Database!"'
                sh '''curl -v -X DELETE http://my-java-app-container:8081/message/2 > delete_response.txt; cat delete_response.txt'''
                sh '''grep "Message with ID 2 deleted" delete_response.txt'''
            }
        }
    }
}
