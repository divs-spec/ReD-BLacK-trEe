import React, { useMemo } from "react";

function layoutTree(root) {
  const nodes = [];
  const edges = [];
  const positions = new Map();

  let index = 0;
  let maxDepth = 0;

  function traverse(node, depth) {
    if (!node) return;
    maxDepth = Math.max(maxDepth, depth);

    traverse(node.left, depth + 1);

    const x = 60 + index * 80;
    const y = 60 + depth * 100;
    positions.set(node, { x, y });
    nodes.push({ node, x, y });
    index += 1;

    traverse(node.right, depth + 1);
  }

  traverse(root, 0);

  function collectEdges(node) {
    if (!node) return;
    const pos = positions.get(node);

    if (node.left) {
      const leftPos = positions.get(node.left);
      edges.push({ x1: pos.x, y1: pos.y, x2: leftPos.x, y2: leftPos.y });
    }

    if (node.right) {
      const rightPos = positions.get(node.right);
      edges.push({ x1: pos.x, y1: pos.y, x2: rightPos.x, y2: rightPos.y });
    }

    collectEdges(node.left);
    collectEdges(node.right);
  }

  collectEdges(root);

  return {
    nodes,
    edges,
    width: Math.max(700, index * 80 + 120),
    height: Math.max(260, (maxDepth + 1) * 100 + 80),
  };
}

export default function TreeView({ root, mode }) {
  const { nodes, edges, width, height } = useMemo(() => layoutTree(root), [root]);

  if (!root) {
    return (
      <div className="empty-tree">
        No tree to display.
      </div>
    );
  }

  const fillFor = (node) => {
    if (mode === "bst") return "#2563eb";
    if (node.color === "RED") return "#ef4444";
    return "#111827";
  };

  return (
    <svg viewBox={`0 0 ${width} ${height}`} className="tree-svg">
      {edges.map((e, i) => (
        <line
          key={i}
          x1={e.x1}
          y1={e.y1}
          x2={e.x2}
          y2={e.y2}
          stroke="#94a3b8"
          strokeWidth="2"
        />
      ))}

      {nodes.map(({ node, x, y }) => (
        <g key={`${node.value}-${x}-${y}`}>
          <circle cx={x} cy={y} r="23" fill={fillFor(node)} />
          <text
            x={x}
            y={y + 5}
            textAnchor="middle"
            fontSize="16"
            fontWeight="700"
            fill="#ffffff"
          >
            {node.value}
          </text>
        </g>
      ))}
    </svg>
  );
}
