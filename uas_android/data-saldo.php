<?php
require_once('config.php');

//header('Content-Type: application/json');
$data  =array();
$sql = mysqli_query($db, "SELECT 
(
    SELECT SUM(jumlah)
    FROM   transaksi where status='MASUK'
) AS masuk,
(
    SELECT SUM(jumlah)
    FROM   transaksi where status='KELUAR'
) AS keluar");
if ($sql->num_rows > 0) {
while ($hasil=mysqli_fetch_assoc($sql)) {
$data[] = array("masuk" =>intval($hasil['masuk']),"keluar" =>intval($hasil['keluar']),"total" =>$hasil['masuk']-$hasil['keluar'] );
//  $data[] = $hasil;
//$total = $hasil['masuk']-$hasil['keluar'];

}
echo json_encode(array("kode" => 1, "pesan"=>$data));
	}else{
		echo json_encode(array("kode" => 0, "pesan"=>"data tidak ditemukan"));
	}

?>