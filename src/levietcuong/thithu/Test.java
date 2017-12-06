package levietcuong.thithu;

import java.util.ArrayList;
import java.util.List;

import levietcuong.partToeic.CauHoi;
import levietcuong.partToeic.QuanLyPart1;
import levietcuong.partToeic.QuanLyPart2;
import levietcuong.partToeic.QuanLyPart3;
import levietcuong.partToeic.QuanLyPart4;
import levietcuong.partToeic.QuanLyPart5;
import levietcuong.partToeic.QuanLyPart6;

public class Test {
	private String name;
	private QuanLyPart1 part1;
	private QuanLyPart2 part2;
	private QuanLyPart3 part3;
	private QuanLyPart4 part4;
	private QuanLyPart5 part5;
	private QuanLyPart6 part6;
	
	public QuanLyPart1 getPart1() {
		return part1;
	}

	public Test(String name) {
		super();
		this.name = name;
	}
	
	public QuanLyPart2 getPart2() {
		return part2;
	}

	public QuanLyPart3 getPart3() {
		return part3;
	}

	public QuanLyPart4 getPart4() {
		return part4;
	}

	public QuanLyPart5 getPart5() {
		return part5;
	}

	public QuanLyPart6 getPart6() {
		return part6;
	}

	public void setPart1(QuanLyPart1 part1) {
		this.part1 = part1;
	}

	public void setPart2(QuanLyPart2 part2) {
		this.part2 = part2;
	}

	public void setPart3(QuanLyPart3 part3) {
		this.part3 = part3;
	}

	public void setPart4(QuanLyPart4 part4) {
		this.part4 = part4;
	}

	public void setPart5(QuanLyPart5 part5) {
		this.part5 = part5;
	}

	public void setPart6(QuanLyPart6 part6) {
		this.part6 = part6;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Bộ đề Toeic full" + name;
	}
}
