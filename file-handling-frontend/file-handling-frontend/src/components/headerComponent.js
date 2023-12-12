import { NavLink, Route, Routes } from "react-router-dom";
import ImageUpload from "./imageUpload";
import ExcelDownload from "./excelDownload";
import "../css/header.css";


export default function HeaderComponent (){


    return(
        <>
        <header className="header">
            <NavLink to={"/"}  className="section">Image section</NavLink>
            <NavLink to={"/fileSection"} className="section">File section</NavLink>
        </header>
        <Routes>
            <Route path="/" element={<ImageUpload/>}></Route>
            <Route path="/fileSection" element={<ExcelDownload/>}></Route>
        </Routes>
        </>
    )
}