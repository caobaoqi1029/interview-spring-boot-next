import React from "react";
import './index.css'

export default function GlobalFooter() {
    const year = new Date().getFullYear();
    return (
        <div className={"globalFooter"}>
            <div>© {year} Made with caobaoqi1029</div>
            <div>mcddhub</div>
        </div>
    );
}
