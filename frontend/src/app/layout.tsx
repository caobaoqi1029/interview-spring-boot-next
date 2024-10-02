"use client";
import { AntdRegistry } from "@ant-design/nextjs-registry";
import React, {useCallback, useEffect} from "react";
import BasicLayout from "@/layouts/BasicLayout";
import "./globals.css";

export default function RootLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="zh">
            <body>
                <AntdRegistry>
                    <BasicLayout>{children}</BasicLayout>
                </AntdRegistry>
            </body>
        </html>
    );
}
