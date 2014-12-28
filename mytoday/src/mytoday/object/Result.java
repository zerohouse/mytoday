package mytoday.object;

public class Result {
	private boolean success;
	private String error;

	public Result(boolean success, String error) {
		this.success = success;
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getError() {
		return error;
	}
	

}
