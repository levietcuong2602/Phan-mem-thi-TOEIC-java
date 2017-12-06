package levietcuong.partToeic;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import levietcuong.thithu.Test;

public class LoadBoCauHoi {
	public ArrayList<CauHoi> lstCauHoi;
	public Test test, testRandom;
	private int testID, partID;

	public LoadBoCauHoi() {
		// TODO Auto-generated constructor stub
		this.testRandom = LoadRandomTest();
	}
	public LoadBoCauHoi(int testID, int partID) {
		super();
		this.testID = testID;
		this.partID = partID;
		this.lstCauHoi = loadBoCauHoi(testID, partID);
	}

	public LoadBoCauHoi(int testID) {
		// TODO Auto-generated constructor stub
		this.testID = testID;
		this.test = loadTest();
	}
	
	public Test loadTest(){
		ArrayList<CauHoi> lstpart1 = loadBoCauHoi(testID, 1);
		String Path1 = "dethi/part1/" + testID + ".mp3";
		QuanLyPart1 boCauPart1 = new QuanLyPart1(lstpart1);
		boCauPart1.setUrlAudio(Path1);
		boCauPart1.setThuTuDe(testID);
		
		ArrayList<CauHoi> lstpart2 = loadBoCauHoi(testID, 2);
		String Path2 = "dethi/part2/" + testID + ".mp3";
		QuanLyPart2 boCauPart2 = new QuanLyPart2(lstpart2);
		boCauPart2.setUrlAudio(Path2);
		boCauPart2.setThuTuDe(testID);
		
		ArrayList<CauHoi> lstpart3 = loadBoCauHoi(testID, 3);
		String Path3 = "dethi/part3/" + testID + ".mp3";
		QuanLyPart3 boCauPart3 = new QuanLyPart3(lstpart3);
		boCauPart3.setUrlAudio(Path3);
		boCauPart3.setThuTuDe(testID);
		
		ArrayList<CauHoi> lstpart4 = loadBoCauHoi(testID, 4);
		String Path4 = "dethi/part4/" + testID + ".mp3";
		QuanLyPart4 boCauPart4 = new QuanLyPart4(lstpart4);
		boCauPart4.setUrlAudio(Path4);
		boCauPart4.setThuTuDe(testID);
		
		ArrayList<CauHoi> lstpart5 = loadBoCauHoi(testID, 5);
		QuanLyPart5 boCauPart5 = new QuanLyPart5(lstpart5);
		boCauPart5.setThuTuDe(testID);
		
		ArrayList<CauHoi> lstpart6 = loadBoCauHoi(testID, 6);
		QuanLyPart6 boCauPart6 = new QuanLyPart6(lstpart6);
		boCauPart6.setThuTuDe(testID);
		
		Test test = new Test(testID+"");
		test.setPart1(boCauPart1);
		test.setPart2(boCauPart2);
		test.setPart3(boCauPart3);
		test.setPart4(boCauPart4);
		test.setPart5(boCauPart5);
		test.setPart6(boCauPart6);
		
		return test;
	}

	public ArrayList<CauHoi> loadBoCauHoi(int testid, int partid) {
		ArrayList<CauHoi> lstBoCau1 = new ArrayList<>();
		String imageURL = "";
		String TraLoiA = "";
		String TraLoiB = "";
		String TraLoiC = "";
		String TraLoiD = "";
		String DapAn = "";
		String LichSuLam = "";
		String deBai, doanHoiThoai;

		try {
			String sql = "select* from cauhoi where testid = ? and partid=?";
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/dethi", "root",
					"vietcuong");
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1, testid);
			ps.setInt(2, partid);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				imageURL = rs.getString("ImageURL");
				TraLoiA = rs.getString("TraLoiA");
				TraLoiB = rs.getString("TraLoiB");
				TraLoiC = rs.getString("TraLoiC");
				TraLoiD = rs.getString("TraLoiD");
				DapAn = rs.getString("DapAn");
				LichSuLam = rs.getString("LichSuLam");
				deBai = rs.getString("DeBai");
				doanHoiThoai = rs.getString("HoiThoai");

				lstBoCau1.add(new CauHoi(imageURL, TraLoiA, TraLoiB, TraLoiC, TraLoiD, DapAn, LichSuLam, deBai,
						doanHoiThoai));				
			}

			return lstBoCau1;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public Test LoadRandomTest(){
		 Random r = new Random();
		 
		int indexpart1 = r.nextInt(CountTest(1).size())+ 1;
		ArrayList<CauHoi> lstpart1 = loadBoCauHoi(indexpart1, 1);
		String Path1 = "dethi/part1/" + indexpart1 + ".mp3";
		QuanLyPart1 boCauPart1 = new QuanLyPart1(lstpart1);
		boCauPart1.setUrlAudio(Path1);
		
		int indexpart2 = r.nextInt(CountTest(2).size()) + 1;
		ArrayList<CauHoi> lstpart2 = loadBoCauHoi(indexpart2, 2);
		String Path2 = "dethi/part2/" + indexpart2 + ".mp3";
		QuanLyPart2 boCauPart2 = new QuanLyPart2(lstpart2);
		boCauPart2.setUrlAudio(Path2);
		
		int indexpart3 = r.nextInt(CountTest(3).size())+ 1;
		ArrayList<CauHoi> lstpart3 = loadBoCauHoi(indexpart3, 3);
		String Path3 = "dethi/part3/" + indexpart3 + ".mp3";
		QuanLyPart3 boCauPart3 = new QuanLyPart3(lstpart3);
		boCauPart3.setUrlAudio(Path3);
		
		int indexpart4 = r.nextInt(CountTest(4).size())+ 1;
		ArrayList<CauHoi> lstpart4 = loadBoCauHoi(indexpart4, 4);
		String Path4 = "dethi/part4/" + indexpart4 + ".mp3";
		QuanLyPart4 boCauPart4 = new QuanLyPart4(lstpart4);
		boCauPart4.setUrlAudio(Path4);
		
		ArrayList<CauHoi> lstpart5 = loadCauHoiReading(5);
		QuanLyPart5 boCauPart5 = new QuanLyPart5(lstpart5);
		
		ArrayList<CauHoi> lstpart6 = loadCauHoiReading(6);
		QuanLyPart6 boCauPart6 = new QuanLyPart6(lstpart6);
		
		Test test = new Test(testID+"");
		test.setPart1(boCauPart1);
		test.setPart2(boCauPart2);
		test.setPart3(boCauPart3);
		test.setPart4(boCauPart4);
		test.setPart5(boCauPart5);
		test.setPart6(boCauPart6);
		
		return test;
	}
	
	public ArrayList<CauHoi> loadCauHoiReading(int partid){
		String imageURL = "";
		String TraLoiA = "";
		String TraLoiB = "";
		String TraLoiC = "";
		String TraLoiD = "";
		String DapAn = "";
		String LichSuLam = "";
		String deBai, doanHoiThoai;
		ArrayList<CauHoi> lstCauHoi = new ArrayList<>();
		Connection conn = getConnect();
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement("select * from cauhoi where partid = ?");
			ps.setInt(1, partid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				imageURL = rs.getString("ImageURL");
				TraLoiA = rs.getString("TraLoiA");
				TraLoiB = rs.getString("TraLoiB");
				TraLoiC = rs.getString("TraLoiC");
				TraLoiD = rs.getString("TraLoiD");
				DapAn = rs.getString("DapAn");
				LichSuLam = rs.getString("LichSuLam");
				deBai = rs.getString("DeBai");
				doanHoiThoai = rs.getString("HoiThoai");

				lstCauHoi.add(new CauHoi(imageURL, TraLoiA, TraLoiB, TraLoiC, TraLoiD, DapAn, LichSuLam, deBai,
						doanHoiThoai));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.shuffle(lstCauHoi);
		ArrayList<CauHoi> lstCauHoi2 = new ArrayList<>();
		if(partid == 5){
			for(int i = 0; i < 40; i++){
				lstCauHoi2.add(lstCauHoi.get(i));
			}
		}else if(partid == 6){
			for(int i = 0; i < 12; i++){
				lstCauHoi2.add(lstCauHoi.get(i));
			}
		}
		Collections.shuffle(lstCauHoi2);
		return lstCauHoi2;
	}
	
	public ArrayList<Integer> CountTest(int partid){
		ArrayList<Integer> count = new ArrayList<>();
		Connection conn =  getConnect();
		try {
			PreparedStatement st = (PreparedStatement) conn.prepareStatement("select distinct testid from cauhoi where partid=?");
			st.setInt(1, partid);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				count.add(rs.getInt("testid"));
			}
			while(rs.next()){
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return count;
	}
	
	public Connection getConnect(){
		Connection con = null;
		try {
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/dethi", "root", "vietcuong");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void main(String[] args) {
		List<Integer> lst = new LoadBoCauHoi().CountTest(1);
		Collections.shuffle(lst);
		
		for (Integer i : lst) {
			System.out.println(i);
		}
		
		System.out.println(lst.size());
	}
}
