#!/usr/bin/env groovy

def call() {
    def hi = Hudson.instance
    def pname = env.JOB_NAME.split('/')[0]

    // get the previous build
    def build = hi.getItem(pname).getItem(env.JOB_BASE_NAME).getBuilds()[1]
    def exec = build.getExecutor()

    if (build.number != currentBuild.number && exec != null) {
        exec.interrupt(
            Result.ABORTED,
            new CauseOfInterruption.UserInterruption(
                "Aborted by #${currentBuild.number}"
            )
        )
        println("Aborted previous running build #${build.number}")
    }
}
