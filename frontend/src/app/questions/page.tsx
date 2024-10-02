import styles from "./page.module.css";
import { Button } from "antd";

export default function Questions() {
    return (
        <main className={styles.main}>
            <div className={styles.description}>
                <Button type={"primary"}>Questions</Button>
            </div>
        </main>
    );
}
