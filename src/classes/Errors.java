package classes;

public enum Errors {
    LOG_IN_ERROR("1337", "Ошибка при входе в аккаунт, возможно, данные для входа поменялись, попробуйте перезайти в аккаунт"), SERVER_ERROR("228", "Ошибка сервера, повторите операцию позднее"),
    SERVER_DOES_NOT_HAVE_FILE("337", "Такого файла нет, возможно, он был уже удалён на сервере"), INCORRECT_JOB_TO_STUDENT("777", "В ваших работах такого файла нет, обновите список заданий"),
    INCORRECT_REQUEST("993", "Некоректный запрос на сервер"), ITEM_DOES_NOT_EXIST("123", "Такого предмета не существует"), OK("", ""), NO_INTERNET_CONNECTION("0", "Отсутствует интренет-соединение, проверьте подключение к интернету");
    String ErrorCode;
    String ErrorDescription;
    Errors(String _ErrorCode, String _ErrorDescription){
        ErrorCode = _ErrorCode;
        ErrorDescription = _ErrorDescription;
    }
    public static Errors getError(String errorCode){
        for (Errors err: Errors.values()) {
            if(err.ErrorCode.equals(errorCode)) return err;
        }
        return Errors.OK;
    }
}
