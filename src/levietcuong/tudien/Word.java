package levietcuong.tudien;

public class Word {
	private String tuAnh;
	private String tuViet;
	private String imageURl;
	private String audioURL;

	public String getImageURl() {
		return imageURl;
	}

	public void setImageURl(String imageURl) {
		this.imageURl = imageURl;
	}

	public String getTuAnh() {
		return tuAnh;
	}

	public void setTuAnh(String tuAnh) {
		this.tuAnh = tuAnh;
	}

	public String getTuViet() {
		return tuViet;
	}

	public void setTuViet(String tuViet) {
		this.tuViet = tuViet;
	}

	public String getAudioURL() {
		return audioURL;
	}

	public void setAudioURL(String audioURL) {
		this.audioURL = audioURL;
	}

	public Word(String tuAnh, String tuViet, String imageURl, String audioURL) {
		super();
		this.imageURl = imageURl;
		this.tuAnh = tuAnh;
		this.tuViet = tuViet;
		this.audioURL = audioURL;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return tuAnh + "\t";
	}
}
