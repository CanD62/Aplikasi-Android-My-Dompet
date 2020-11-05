<?php
require_once('config.php');

	$transaksi_id = $_REQUEST['transaksi_id'];
	$status=$_REQUEST['status'];
	$jumlah=$_REQUEST['jumlah'];
	$jumlah= str_replace(",", "", $jumlah);
	$keterangan=$_REQUEST['keterangan'];
	$tanggal= $_REQUEST['tanggal'];
	if(empty($status)){
	echo json_encode(array("kode" => 0, "pesan"=>"kosong"));

	} else{

	$query = "UPDATE transaksi SET status='$status', jumlah='$jumlah', keterangan='$keterangan', tanggal='$tanggal' where transaksi_id='$transaksi_id'" ;
	if (mysqli_query($db, $query)) {
               echo json_encode(array("kode" => 1, "pesan"=>"Sukses"));
            } else {
               echo json_encode(array("kode" => 0, "pesan"=>"gagal"));
            }
            }
?>