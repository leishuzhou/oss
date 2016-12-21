/**
 * @Title: ExceptionProccess.java
 * @Package com.eduspace.eduplatform.wares.exception
 * @Description: TODO
 * @author tbc
 * @date 2015年11月28日 下午9:55:24
 * @version
 */
package com.yijiajiao.oss.exception;

import com.eduspace.eduplatform.util.eduspacereturnvalue.ResultWrapper;
import com.eduspace.eduplatform.util.eduspacereturnvalue.ResultWrappers;
import com.eduspace.eduplatform.util.exception.MyException;
import com.eduspace.eduplatform.util.exception.NoDataException;
import com.eduspace.eduplatform.util.exception.OtherException;
import com.eduspace.eduplatform.util.exception.ParamException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Random;

/**
 * @author tbc e-eduspace
 * @version 1.0 create:{2015年11月28日 下午9:55:24}
 */
@ControllerAdvice
public class ExceptionProcess  {
    Logger log = LoggerFactory.getLogger(ExceptionProcess.class);

    private final String JSON_FORMAT_ERROR = "[json格式错误]";
    // private final String REQUEST_METHOE_ERROR = "[请求方法错误]";
    // private final String REQUEST_PARAMETER = "[请求参数错误]";

    @ExceptionHandler(MyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResultWrapper processMyException(MyException e) {
        String requestId = e.getRequestId() == null ? new String(Integer.toString(new Random().nextInt(99999)))
                : e.getRequestId();
        log.debug("@@@@@抛出MyException异常>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.error("requestId: {}", requestId);
        log.error("message: {}", e.getMessage());
        log.error("异常堆栈信息>>>>>：");
        for (StackTraceElement s : e.getStackTrace()) {
            log.error(s.toString());
        }
        return ResultWrappers.getInstance().r(e.getCode(), e.getMessage(), e.getResult(), requestId);
    }

    /**
     * 处理数据库约束异常
     *
     * @param e
     * @return
     * @author tbc
     * @version 1.0 {2016年10月17日 下午1:35:30}
     */
    @ExceptionHandler(MySQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public ResultWrapper pMySQLIntegrityConstraintViolationException(MySQLIntegrityConstraintViolationException e) {
        log.info("违反数据库约束->");
        log.error(e.getMessage());
//		com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
        String requestId = new String(Integer.toString(new Random().nextInt(99999)));
        return ResultWrappers.getInstance().r("500", "Data duplication", null, requestId);
    }

    /**
     * 经过spring包装的约束异常
     *
     * @param e
     * @return
     * @author tbc
     * @version 1.0 {2016年10月17日 下午1:35:30}
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public ResultWrapper pDuplicateKeyException(DuplicateKeyException e) {
        log.error("Exception: {}", e.getClass().getName());
        log.error("重复数据，违反唯一约束");
        log.error(e.getMessage());
        String requestId = Integer.toString(new Random().nextInt(99999));
        return ResultWrappers.getInstance().r("500", "Data duplication", "重复数据，违反唯一约束", requestId);
    }

    /**
     * 当参数手动校验不成功时，手动抛出此异常
     *
     * @param e .
     * @return
     * @author tbc tianbencai@e-eduspace.com
     * @version 1.0 {2016年1月6日 下午1:43:15}
     */
    @ExceptionHandler(ParamException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultWrapper processParamException(ParamException e) {
        log.debug("@@@@@ 抛出ParamException,参数不正确>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        String requestId = e.getRequestId() == null ? new String(Integer.toString(new Random().nextInt(99999)))
                : e.getRequestId();
        return ResultWrappers.getInstance().r(e.getCode(), e.getMessage(), e.getResult(), requestId);
    }

    @ExceptionHandler(NoDataException.class)
    // @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ResponseBody
    public ResultWrapper processNoDataException(NoDataException e) {
        log.debug("@@@@@ 抛出NoDataException,没有查到数据>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        String requestId = e.getRequestId() == null ? new String(Integer.toString(new Random().nextInt(99999)))
                : e.getRequestId();
        log.error("requestId: {}", requestId);
        log.error(e.getMessage());
        return new ResultWrapper(e.getCode(), e.getMessage(), e.getResult(), requestId);
    }

    /**
     * 其它异常，不打印堆栈信息
     *
     * @param e
     * @return
     * @author tbc tianbencai@e-eduspace.com
     * @version 1.0 {2016年1月11日 上午10:41:43}
     */
    @ExceptionHandler(OtherException.class)
    @ResponseBody
    public ResultWrapper processOtherException(OtherException e) {
        log.debug("@@@@@ OtherException,没有查到数据>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        String requestId = e.getRequestId() == null ? new String(Integer.toString(new Random().nextInt(99999)))
                : e.getRequestId();
        log.error("requestId: {}", requestId);
        log.error(e.getMessage());
        return new ResultWrapper(e.getCode(), e.getMessage(), e.getResult(), requestId);
    }

    /***********************************************************************/
    /***********************************************************************/
    /***********************************************************************/
    /***********************************************************************/

    /***********************************************************************/
    /***********************************************************************/

    @ExceptionHandler(TypeMismatchDataAccessException.class)
    public ResultWrapper processTypeMismatchDataAccessException(TypeMismatchDataAccessException e) {

        return ResultWrappers.getInstance().exception(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultWrapper processMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // validateexception
        log.error("#########应用到所有@RequestMapping注解的方法，在其抛出Exception异常时执行");
        log.error(e.getMessage());
        return ResultWrappers.getInstance().exception("[request method ] " + e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultWrapper processHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        // json格式错误
        log.error("#########应用到所有@RequestMapping注解的方法，在其抛出HttpMessageNotReadableException异常时执行");
        log.error(e.getMessage());
        return ResultWrappers.getInstance().exception(this.JSON_FORMAT_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultWrapper processException(NativeWebRequest request, Exception e) {
        String requestId = new String(Integer.toString(new Random().nextInt(99999)));
        log.error("@@@@@抛出Exception异常>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.error("Exception: {}", e.getClass().getName());
        log.error("requestId: {}", requestId);
        log.error("message: {}", e.getMessage());
        log.error("异常堆栈信息>>>>>：");
        for (StackTraceElement s : e.getStackTrace()) {
            // log.error("文件：" + s.getFileName());
            // log.error("类名：" + s.getClassName());
            // log.error("方法：" + s.getMethodName());
            // log.error("行数：" + s.getLineNumber());
            log.error(s.toString());
        }
        return ResultWrappers.getInstance().r("500", "[throw Exception]请求错误" + e.getMessage(), null, requestId);
    }

    // @ModelAttribute
    // public void newUser() {
    // System.out.println("@ModelAttribute============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");
    // }

    // @InitBinder
    // public void initBinder(WebDataBinder binder) {
    // System.out.println("@InitBinder============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
    // }

}
