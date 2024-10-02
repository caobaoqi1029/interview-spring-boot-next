import styles from "./page.module.css";
import { Button } from "antd";

export default function Home() {
    return (
        <main className={styles.main}>
            <div className={styles.description}>
                <Button type={"primary"}>Home</Button>
            </div>
        </main>
    );
}
