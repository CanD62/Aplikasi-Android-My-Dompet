<?php
require_once('config.php');

	$transaksi_id = $_REQUEST['transaksi_id'];
	
	if(empty($transaksi_id)){
	echo json_encode(array("kode" => 0, "pesan"=>"kosong"));

	} else{

	$query = "DELETE from transaksi where transaksi_id='$transaksi_id'" ;
	if (mysqli_query($db, $query)) {
               echo json_encode(array("kode" => 1, "pesan"=>"Sukses"));
            } else {
               echo json_encode(array("kode" => 0, "pesan"=>"gagal"));
            }
            }
?>