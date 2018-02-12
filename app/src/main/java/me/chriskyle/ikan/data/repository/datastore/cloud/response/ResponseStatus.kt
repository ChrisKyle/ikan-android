package me.chriskyle.ikan.data.repository.datastore.cloud.response

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
object ResponseStatus {

    const val STATUS_CODE_10000 = 10000  // success
    const val STATUS_CODE_10001 = 10001  // token expired
    const val STATUS_CODE_10002 = 10002  // refreshToken expired
    const val STATUS_CODE_10003 = 10003  //
    const val STATUS_CODE_10004 = 10004  //
    const val STATUS_CODE_10005 = 10005  // feed does not exist
    const val STATUS_CODE_10006 = 10006  // account does not exist
    const val STATUS_CODE_10007 = 10007  // http bad request
    const val STATUS_CODE_10008 = 10008  //
    const val STATUS_CODE_10009 = 10009  //
    const val STATUS_CODE_10010 = 10010  // internal server error
    const val STATUS_CODE_10011 = 10011  // login required
    const val STATUS_CODE_10012 = 10012  // feed like fail
    const val STATUS_CODE_10013 = 10013  // feed buy fail
    const val STATUS_CODE_10014 = 10014  // balance is not enough
}
