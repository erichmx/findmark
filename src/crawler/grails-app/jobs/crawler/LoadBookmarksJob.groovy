package crawler

class LoadBookmarksJob {
    static triggers = {
      simple repeatInterval: 5000l // execute job once in 5 seconds
    }

    def execute() {
        // execute job
        println("hello3")
    }
}
