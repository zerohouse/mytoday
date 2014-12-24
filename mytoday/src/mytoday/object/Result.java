package mytoday.object;

public class Result {
	private boolean success;
	private String errorMesage;

	public Result(boolean success, String errorMesage) {
		super();
		this.success = success;
		this.errorMesage = errorMesage;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getErrorMesage() {
		return errorMesage;
	}
	

}
