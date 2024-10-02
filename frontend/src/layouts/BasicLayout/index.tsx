"use client";
import { GithubFilled, LogoutOutlined, SearchOutlined } from "@ant-design/icons";
import { ProLayout } from "@ant-design/pro-components";
import { Dropdown, Input } from "antd";
import React from "react";
import { usePathname } from "next/navigation";
import Image from "next/image";
import Link from "next/link";
import GlobalFooter from "@/components/GlobalFooter";
import './index.css'
import {menus} from "@/config/menu";

const SearchInput = () => {
    return (
        <div
            key="SearchOutlined"
            aria-hidden
            style={{
                display: "flex",
                alignItems: "center",
                marginInlineEnd: 24,
            }}
            onMouseDown={(e) => {
                e.stopPropagation();
                e.preventDefault();
            }}
        >
            <Input
                style={{
                    borderRadius: 4,
                    marginInlineEnd: 12,
                }}
                prefix={<SearchOutlined />}
                placeholder="搜索题目"
                variant="borderless"
            />
        </div>
    );
};

interface Props {
    children: React.ReactNode;
}

export default function BasicLayout({ children }: Props) {
    const pathname = usePathname();
    if (typeof document === "undefined") {
        return <div />;
    }
    return (
        <div
            id="basicLayout"
            style={{
                height: "100vh",
                overflow: "auto",
            }}
        >
            <ProLayout
                layout={"top"}
                title={"鸡你太美 KFC"}
                logo={<Image src={"/next.svg"} height={32} width={32} alt={"网站图标"} />}
                location={{
                    pathname,
                }}
                avatarProps={{
                    src: "https://gw.alipayobjects.com/zos/antfincdn/efFD%24IOql2/weixintupian_20170331104822.jpg",
                    size: "small",
                    title: "七妮妮",
                    render: (props, dom) => {
                        return (
                            <Dropdown
                                menu={{
                                    items: [
                                        {
                                            key: "logout",
                                            icon: <LogoutOutlined />,
                                            label: "退出登录",
                                        },
                                    ],
                                }}
                            >
                                {dom}
                            </Dropdown>
                        );
                    },
                }}
                actionsRender={(props) => {
                    if (props.isMobile) return [];
                    return [<SearchInput key="SearchInput" />, <GithubFilled key="GithubFilled" />];
                }}
                headerTitleRender={(logo, title, _) => {
                    const defaultDom = (
                        <a>
                            {logo}
                            {title}
                        </a>
                    );
                    if (typeof window === "undefined") return defaultDom;
                    if (document.body.clientWidth < 1400) {
                        return defaultDom;
                    }
                    if (_.isMobile) return defaultDom;
                    return <>{defaultDom}</>;
                }}
                footerRender={(props) => {
                    return <GlobalFooter />;
                }}
                onMenuHeaderClick={(e) => console.log(e)}
                menuDataRender={() => {
                    return menus
                }}
                menuItemRender={(item, dom) => (
                    <Link href={item.path || "/"} target={item.target}>
                        {dom}
                    </Link>
                )}
            >
                {children}
            </ProLayout>
        </div>
    );
}
