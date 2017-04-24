package crawler

class Group {
    String title
    Date added

    static searchable = {
        root false

        title boost:100
    }

    static constraints = {
        title nullable: false, blank: false, maxSize: 1024
    }

    static mapping = {
        table 'bgroup'
    }
}
