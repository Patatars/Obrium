package classes;

public enum URLs {
    LOGIN_URL("http://cyberes.admin-blog.ru/LNTest/API/Student/logIn.php"),
    GETFILE_URL("http://cyberes.admin-blog.ru/LNTest/API/Student/getFile.php"),
    ALLCOMPLETE_URL("http://cyberes.admin-blog.ru/LNTest/API/Student/jobDone.php"),
    GET_ITEMCODE_BY_FULL_NAME("http://cyberes.admin-blog.ru/LNTest/API/getItemCodeByFullName.php"),
    GET_TIME("http://cyberes.admin-blog.ru/LNTest/API/getTime.php");

    final String url;

    URLs(String url){
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
