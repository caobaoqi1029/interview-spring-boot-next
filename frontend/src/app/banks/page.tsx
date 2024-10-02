import styles from "./page.module.css";
import { Button } from "antd";

export default function Banks() {
    return (
        <main className={styles.main}>
            <div className={styles.description}>
                <Button type={"primary"}>Banks</Button>
            </div>
        </main>
    );
}
