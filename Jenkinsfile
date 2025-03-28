pipeline {
  agent any

  environment {
    DOCKERHUB_CREDENTIALS = credentials('Dcoker-cred')
    VERSION = "${env.BUILD_ID}"

  }

  tools {
    maven "Maven"
  }

  stages {

    stage('Maven Build'){
        steps{
        sh 'mvn clean package  -DskipTests'
        }
    }

    /* stage('Run Tests') {
      steps {
        sh 'mvn test'
      }
    }*/

    stage('SonarQube Analysis') {
  steps {
    sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://34.244.177.187:9000/ -Dsonar.login=squ_6a7503d2292a24ca50290f17a99b32a5fc3a76e4'
  }
}


   stage('Check code coverage') {
            steps {
                script {
                    def token = "squ_6a7503d2292a24ca50290f17a99b32a5fc3a76e4"
                    def sonarQubeUrl = "http://34.244.177.187:9000/api"
                    def componentKey = "com.sree:restuarantlisting"
                    def coverageThreshold = 80.0

                    def response = sh (
                        script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
                        returnStdout: true
                    ).trim()

                    def coverage = sh (
                        script: "echo '${response}' | jq -r '.component.measures[0].value'",
                        returnStdout: true
                    ).trim().toDouble()

                    echo "Coverage: ${coverage}"

                    if (coverage < coverageThreshold) {
                        error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                    }
                }
            }
        }


      stage('Docker Build and Push') {
      steps {
          sh 'echo $Dcoker-cred | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
          sh 'docker build -t sree27/restaurant-listing-service:${VERSION} .'
          sh 'docker push sree27/restaurant-listing-service:${VERSION}'
      }
    }


     stage('Cleanup Workspace') {
      steps {
        deleteDir()

      }
    }



    stage('Update Image Tag in GitOps') {
      steps {
         checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[ credentialsId: 'git-ssh', url: 'git@github.com:sreelu27/deployment-food-delivery.git']])
        script {
       sh '''
          sed -i "s/image:.*/image: sree27\\/restaurant-listing-service:${VERSION}/" restaurant-manifest.yml
        '''
          sh 'git checkout main'
          sh 'git add .'
          sh 'git commit -m "Update image tag"'
        sshagent(['git-ssh'])
            {
                  sh('git push')
            }
        }
      }
    }

  }

}