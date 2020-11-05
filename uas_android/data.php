<?php
require_once('config.php');

//header('Content-Type: application/json');
$data  =array();
$sql = mysqli_query($db, "SELECT * FROM transaksi");
if ($sql->num_rows > 0) {
while ($hasil=mysqli_fetch_assoc($sql)) {
 $data[] = $hasil;
  

}
echo json_encode(array("kode" => 1, "data"=>$data));
	}else{
		echo json_encode(array("kode" => 0, "data"=>"data tidak ditemukan"));
	}

?>