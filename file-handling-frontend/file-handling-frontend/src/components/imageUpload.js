import axios from "axios";
import {useEffect, useState} from "react";
import {saveAs} from "file-saver";
import genericFileDownloader from "../functions/genericFileDownloader";
import "../css/ImageUpload.css";

export default function ImageUpload(){

    const [url,setUrl] = useState("");
    const [imageData,setImageData] = useState([]);

    const request = {
        file: null,
        name: "",
        description: ""
    } ;

    const uploadImage = async ()=>{

        const formData = new FormData();
        formData.append("image",request.file);
        formData.append("name",request.name);
        formData.append("description",request.description);

        
        try{
        const response = await axios.post("http://localhost:8090/uploadImage",formData);
        request.file = null;
        request.name = "";
        request.description = "";
        console.log(response);
        }catch(error){
            console.error(error);
        }

    }

    const handleView =  async(e,imageName)=>{

        const anchorUrl = await download(e,imageName);
        setUrl(anchorUrl);

    }

    const handleDownload = async (e,imageName) => {
        
        const anchorUrl  = await download(e,imageName)
        saveAs(anchorUrl,`${imageName}.png`);

    }

    const download = async (e,imageName)=>{

        if(url !== null ){
            URL.revokeObjectURL(url);
            console.log("url revoked!!");
        }
        return await genericFileDownloader(`getImage/${e}`,`${imageName}.png`)
    }

    useEffect( ()=>{

        async function getImagesMetaData (){
            const response = await axios.get(`http://localhost:8090/getImages`).then(response=>response).catch(error=>console.log("some error",error));
            console.log("response brother ",response);

            if(response !== (null || undefined ) && response.data !== null){
                console.log("Are you still getting here ?")
                setImageData([...(response.data.images)]);
            }  
        }
        console.log("Fetching images meta data");
        getImagesMetaData();

        return () => {setImageData([])};

    },[]);

    return (
        <div>
            <header>
                <h2>Hi Welcome to image upload section</h2>
            </header>
            <section>
                
                <br/>
                <div>
                <span>Enter the name of cricketer: </span>
                <input type="text"  onChange={(e)=>request.name = e.target.value}/> 
                </div>
                <br/>
                <div>
                <span>Enter the description: </span>
                <input type="text"  onChange={(e)=>request.description = e.target.value}/> 
                </div>  
            </section>
            <br></br>
                <input type="file" onChange ={(e)=>request.file = e.target.files[0]} />
            <button onClick={uploadImage} >Upload data</button>
            <div>
            <br></br>
            <div className="viewImage">
            <div className="tableContainer"> 
            <table className="table">
            <thead>
                <tr>
                <th>Sl.No</th>
                <th>Name of cricketer</th>
                <th>Download link</th>
                <th>View link</th>
                </tr> 
            </thead>
            <tbody>
            {imageData !== (null || undefined) && imageData.length>0 && imageData.map((item,index)=>{

             return(
                 <tr key ={index}>
                <td>{index+1}</td>
                <td>{item.imageName}</td>
                <td><span className="button" onClick={(e)=>{handleDownload(item.id,item.imageName)}}>Download</span></td>
                <td><span className="button" onClick={(e)=>{handleView(item.id,item.imageName)}}>View image</span></td>
                </tr>
                   );
            })}
            </tbody>
            </table>
            </div>
            {url.length > 1 && <img src = {url} alt="Unable to load" width={"30%"} height={"200px"}></img>}
            
            </div>
            
            </div>
            
            
        </div>
    )
}