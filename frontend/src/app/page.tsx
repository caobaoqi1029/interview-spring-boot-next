import styles from "./page.module.css";
import { Button } from "antd";
import { listMyQuestionBankVoByPageUsingPost } from "@/api/questionBankController";

export default function Home() {
    listMyQuestionBankVoByPageUsingPost({}).then((res) => {
        console.log(res);
    });
    return (
        <main className={styles.main}>
            <div className={styles.description}>
                <Button type={"primary"}>Home</Button>
            </div>
        </main>
    );
}
