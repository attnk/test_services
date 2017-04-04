package exercise.model;

import java.io.Serializable;

public class SearchError implements Serializable {

	private static final long serialVersionUID = 6041067810602643807L;
	
	private String messega;

	public String getMessega() {
		return messega;
	}

	public void setMessega(String messega) {
		this.messega = messega;
	}

}
