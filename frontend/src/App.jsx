import React, { useEffect, useMemo, useState } from "react";
import { buildTreeDemo } from "./api";
import TreeView from "./components/TreeView";
import "./index.css";

export default function App() {
  const [input, setInput] = useState("10,20,30,15,25,5,1,50,60");
  const [data, setData] = useState(null);
  const [step, setStep] = useState(0);
  const [playing, setPlaying] = useState(false);
  const [error, setError] = useState("");

  const snapshots = data?.snapshots || [];
  const currentSnapshot = snapshots.length ? snapshots[Math.min(step, snapshots.length - 1)] : null;

  useEffect(() => {
    if (!playing || snapshots.length === 0) return;

    const timer = setInterval(() => {
      setStep((prev) => {
        if (prev + 1 >= snapshots.length) return 0;
        return prev + 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, [playing, snapshots.length]);

  const parsedValues = useMemo(() => {
    return input
      .split(",")
      .map((s) => Number(s.trim()))
      .filter((n) => Number.isFinite(n));
  }, [input]);

  const handleBuild = async () => {
    setError("");

    if (parsedValues.length === 0) {
      setError("Enter at least one valid integer.");
      return;
    }

    try {
      setPlaying(false);
      const result = await buildTreeDemo(parsedValues);
      setData(result);
      setStep(Math.max(0, result.snapshots.length - 1));
    } catch (e) {
      setError(e.message || "Something went wrong.");
    }
  };

  return (
    <div className="page">
      <header className="header">
        <h1>BST to Red-Black Tree Demo</h1>
        <p>
          Enter values, build the BST, and watch the Red-Black Tree rebalance step by step.
        </p>
      </header>

      <section className="controls">
        <textarea
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="10,20,30,15,25"
        />

        <div className="button-row">
          <button onClick={handleBuild}>Build Demo</button>
          <button onClick={() => setPlaying((p) => !p)} disabled={snapshots.length === 0}>
            {playing ? "Pause" : "Play"}
          </button>
        </div>

        {snapshots.length > 0 && (
          <div className="slider-row">
            <input
              type="range"
              min="0"
              max={snapshots.length - 1}
              value={step}
              onChange={(e) => setStep(Number(e.target.value))}
            />
            <div className="step-info">
              Step {step + 1} / {snapshots.length}
              <span>{currentSnapshot?.label}</span>
            </div>
          </div>
        )}

        {error && <div className="error">{error}</div>}
      </section>

      <section className="grid">
        <div className="card">
          <h2>BST</h2>
          <TreeView root={data?.bstRoot || null} mode="bst" />
        </div>

        <div className="card">
          <h2>Red-Black Tree</h2>
          <TreeView root={currentSnapshot?.root || null} mode="rb" />
        </div>
      </section>

      <section className="legend">
        <div><span className="dot blue" /> BST node</div>
        <div><span className="dot red" /> Red node</div>
        <div><span className="dot black" /> Black node</div>
      </section>
    </div>
  );
}
