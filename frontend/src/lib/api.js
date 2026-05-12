const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api').replace(/\/$/, '');

async function request(path, options = {}) {
    const response = await fetch(`${API_BASE_URL}${path}`, {
        headers: {
            'Content-Type': 'application/json',
            ...(options.headers ?? {}),
        },
        ...options,
    });

    const isJson = response.headers.get('content-type')?.includes('application/json');
    const data = isJson ? await response.json() : null;

    if (!response.ok) {
        const fieldErrors = data?.fieldErrors ? Object.values(data.fieldErrors).join(' ') : '';
        const message = fieldErrors || data?.message || 'The request could not be completed.';
        throw new Error(message);
    }

    return data;
}

export function login(payload) {
    return request('/auth/login', {
        method: 'POST',
        body: JSON.stringify(payload),
    });
}

export function createAssessment(payload) {
    return request('/assessments', {
        method: 'POST',
        body: JSON.stringify(payload),
    });
}

export function getAssessment(assessmentId) {
    return request(`/assessments/${assessmentId}`);
}
