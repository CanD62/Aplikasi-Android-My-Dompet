<?php
require_once('config.php');

	$transaksi_id = $_REQUEST['transaksi_id'];
	// $status=$_REQUEST['status'];
	// $jumlah=$_REQUEST['jumlah'];
	// $keterangan=$_REQUEST['keterangan'];
	// $tanggal= $_REQUEST['tanggal'];
	if(empty($transaksi_id)){
	echo json_encode(array("kode" => 0, "pesan"=>"kosong"));

	} else{

	$sql = mysqli_query($db, "SELECT * FROM transaksi where transaksi_id='$transaksi_id'") ;
	if ($sql->num_rows > 0) {
while ($hasil=mysqli_fetch_assoc($sql)) {
 $data[] = $hasil;
  

}
echo json_encode(array("kode" => 1, "data"=>$data));
	}else{
		echo json_encode(array("kode" => 0, "data"=>"data tidak ditemukan"));
	}



            }
?>