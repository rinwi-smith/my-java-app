stage('Test App') {
    steps {
        sh 'sleep 60'
        sh 'curl -s http://my-java-app-container:8081 | grep "Hello, Java!"'
        sh 'curl -s http://my-java-app-container:8081/message | grep "Hello, Database!"'
        sh '''curl -s -X POST http://my-java-app-container:8081/message \
              -H "Content-Type: application/json" \
              -d \'{"id":2,"content":"New Message"}\' | grep "Message saved: New Message"'''
    }
}
