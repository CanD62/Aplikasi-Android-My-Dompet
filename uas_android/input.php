<?php
require_once('config.php');
date_default_timezone_set("Asia/Jakarta");
	

	$status=$_REQUEST['status'];
	$jumlah=$_REQUEST['jumlah'];
	$jumlah= str_replace(",", "", $jumlah);
	$keterangan=$_REQUEST['keterangan'];
	$tanggal = date('Y-m-d');
	if(empty($status)){
	echo json_encode(array("kode" => 0, "pesan"=>"kosong"));

	} else{

	$query = "INSERT INTO transaksi SET status='$status', jumlah='$jumlah', keterangan='$keterangan', tanggal='$tanggal'" ;
	if (mysqli_query($db, $query)) {
               echo json_encode(array("kode" => 1, "pesan"=>"Sukses"));
            } else {
               echo json_encode(array("kode" => 0, "pesan"=>"gagal"));
            }
            }
?>