import axios from "axios";


export default async function genericFileDownloader(urlEndPoint,fileName){


        console.log("downloading the excel file")
        try{
        const response = await axios.get(`http://localhost:8090/`+urlEndPoint,{responseType: "blob"});
        const blob = await response.data;
        let urlReference = null;
        if(fileName.endsWith(".pdf")){
             urlReference = URL.createObjectURL(new Blob([blob],{type: "application/pdf"}));  // type mandatory to display pdf in iframe
        }else if(fileName.endsWith(".xlsx")){
             urlReference = URL.createObjectURL(new Blob([blob],{ type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }));
        }else{
            urlReference = URL.createObjectURL(new Blob([blob],{type:"image/png"}));
        }
        // const link = document.createElement('a');
        // link.href = urlReference;
        // link.setAttribute('download', 'ImageDataExcel.xlsx');
        // document.body.appendChild(link);
        // console.log("url created");
        // link.click();
        // link.remove();
        // console.log("blob ",urlReference);
        return urlReference;
        }
        catch(error){
            console.error(error);
        }

    
}