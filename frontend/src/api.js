export async function buildTreeDemo(values) {
  const res = await fetch("/api/trees/demo", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ values }),
  });

  if (!res.ok) {
    throw new Error("Failed to build tree demo");
  }

  return res.json();
}
