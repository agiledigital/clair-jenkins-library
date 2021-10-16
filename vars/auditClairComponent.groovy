/*
 * Toolform-compatible Jenkins 2 Pipeline build step for simple Docker artifacts
 * Zips up the specified directory as long as it contains a Dockerfile
 */

def call(Map config) {
  container('clair-builder') {
    stage('Get Clair report') {
      sh "clairctl report --host ${config.host} --out json ${config.image} > ${config.file}.json"
      sh "clairctl report --host ${config.host} --out text ${config.image} > ${config.file}.txt"
    }

    stage('Archive to Jenkins') {
      archiveArtifacts "${config.file}.json"
      archiveArtifacts "${config.file}.txt"
      try{
        slackUploadFile filePath: "${config.file}.json", initialComment: "${config.image} json report", channel: config.slackThead.threadId
        slackUploadFile filePath: "${config.file}.txt", initialComment: "${config.image} text report", channel: config.slackThead.threadId
      } catch  (e) {
        currentBuild.result = "UNSTABLE"
      }
    }
  }
}
