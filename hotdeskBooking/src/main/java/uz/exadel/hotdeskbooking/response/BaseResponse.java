package uz.exadel.hotdeskbooking.response;


public abstract class BaseResponse {
    protected static final ApiResponse ALREADY_EXISTS = new ApiResponse("Already exists", 409);

    protected static final ApiResponse FORBIDDEN = new ApiResponse("Your role does not allow that", 403); //TODO NEED TO ADD THIS IN OFFICE ADDING PART

    protected static final ApiResponse NOT_FOUND = new ApiResponse("NOT FOUND", 404);

    protected static final ApiResponse SUCCESS = new ApiResponse("OK", 200);

    protected static final ApiResponse SUCCESS_ONLY = new ApiResponse("OK", 200); //SHOULD NOT BE SET OBJECT TO THIS ONE

}
