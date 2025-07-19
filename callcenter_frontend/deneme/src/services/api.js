import apiClient from './apiClient';
import axios from 'axios';

// ————— Operator Management —————
export const getAllOperators = () =>
    apiClient.get('/api/operators/get').then(res => res.data?.data || res.data);

export const getOperatorById = (id) =>
    apiClient.get(`/api/operators/${id}`).then(res => res.data?.data || res.data);

export const createOperator = (payload) =>
    apiClient.post('/api/operators/post', payload).then(res => res.data?.data || res.data);

export const updateOperator = (id, payload) =>
    apiClient.put(`/api/operators/${id}`, payload).then(res => res.data?.data || res.data);

export const deleteOperator = (id) =>
    apiClient.delete(`/api/operators/${id}`).then(res => res.data?.data || res.data);

// ————— Operator-Customer Controller —————

// Operatör, müşteri ve çağrıyı bağla (POST /api/operator-customers/add-call?operatorId=...&customerId=...&callId=...)
export const linkOperatorCustomerCall = ({ operatorId, customerId, callId }) =>
    apiClient.post(
        `/api/operator-customers/add-call?operatorId=${operatorId}&customerId=${customerId}&callId=${callId}`
    ).then(res => res.data);

// Operatörün mailine göre müşteri çağrı istatistiklerini getir
// GET /api/operator-customers/stats/by-email/{operatorEmail}
export const getOperatorCustomerStatsByEmail = (operatorEmail) =>
    apiClient.get(`/api/operator-customers/stats/by-email/${operatorEmail}`)
        .then(res => res.data);

// ————— Service Controller —————
export const getAllServices = () =>
    apiClient.get('/api/services').then(res => res.data?.data || res.data);

export const getServiceById = (id) =>
    apiClient.get(`/api/services/${id}`).then(res => res.data);

export const createService = (payload) =>
    apiClient.post('/api/services', payload).then(res => res.data);

export const updateService = (id, payload) =>
    apiClient.put(`/api/services/${id}`, payload).then(res => res.data);

export const deleteService = (id) =>
    apiClient.delete(`/api/services/${id}`).then(res => res.data);

// ————— Log Controller —————
export const getAllLogs = () =>
    apiClient.get('/api/logs').then(res => res.data?.data || res.data);

export const getLogById = (id) =>
    apiClient.get(`/api/logs/${id}`).then(res => res.data);

export const createLog = (payload) =>
    apiClient.post('/api/logs', payload).then(res => res.data);

export const updateLog = (id, payload) =>
    apiClient.put(`/api/logs/${id}`, payload).then(res => res.data);

export const deleteLog = (id) =>
    apiClient.delete(`/api/logs/${id}`).then(res => res.data);

// ————— Location Controller —————
export const getAllLocations = () =>
    apiClient.get('/api/locations').then(res => res.data?.data || res.data);

export const getLocationById = (id) =>
    apiClient.get(`/api/locations/${id}`).then(res => res.data);

export const createLocation = (payload) =>
    apiClient.post('/api/locations', payload).then(res => res.data);

export const updateLocation = (id, payload) =>
    apiClient.put(`/api/locations/${id}`, payload).then(res => res.data);

export const deleteLocation = (id) =>
    apiClient.delete(`/api/locations/${id}`).then(res => res.data);

export const getCities = () =>
    apiClient.get('/api/locations/cities').then(res => res.data?.data || res.data);

export const getCityById = (id) =>
    apiClient.get(`/api/locations/cities/${id}`).then(res => res.data);

export const createCity = (payload) =>
    apiClient.post('/api/locations/cities', payload).then(res => res.data);

export const updateCity = (id, payload) =>
    apiClient.put(`/api/locations/cities/${id}`, payload).then(res => res.data);

export const deleteCity = (id) =>
    apiClient.delete(`/api/locations/cities/${id}`).then(res => res.data);

export const getDistricts = () =>
    apiClient.get('/api/locations/districts').then(res => res.data?.data || res.data);

export const getDistrictById = (id) =>
    apiClient.get(`/api/locations/districts/${id}`).then(res => res.data);

export const createDistrict = (payload) =>
    apiClient.post('/api/locations/districts', payload).then(res => res.data);

export const updateDistrict = (id, payload) =>
    apiClient.put(`/api/locations/districts/${id}`, payload).then(res => res.data);

export const deleteDistrict = (id) =>
    apiClient.delete(`/api/locations/districts/${id}`).then(res => res.data);

export const getTownships = () =>
    apiClient.get('/api/locations/townships').then(res => res.data?.data || res.data);

export const getTownshipById = (id) =>
    apiClient.get(`/api/locations/townships/${id}`).then(res => res.data);

export const createTownship = (payload) =>
    apiClient.post('/api/locations/townships', payload).then(res => res.data);

export const updateTownship = (id, payload) =>
    apiClient.put(`/api/locations/townships/${id}`, payload).then(res => res.data);

export const deleteTownship = (id) =>
    apiClient.delete(`/api/locations/townships/${id}`).then(res => res.data);

export const getNeighbourhoods = () =>
    apiClient.get('/api/locations/neighbourhoods').then(res => res.data?.data || res.data);

export const createNeighbourhood = (payload) =>
    apiClient.post('/api/locations/neighbourhoods', payload).then(res => res.data);

export const updateNeighbourhood = (id, payload) =>
    apiClient.put(`/api/locations/neighbourhoods/${id}`, payload).then(res => res.data);

export const deleteNeighbourhood = (id) =>
    apiClient.delete(`/api/locations/neighbourhoods/${id}`).then(res => res.data);

// ————— Address Controller (Yeni) —————
export const getAddressById = (id) =>
    apiClient.get(`/api/locations/addresses/${id}`).then(res => res.data);

export const createAddress = (payload) =>
    apiClient.post('/api/locations/addresses', payload).then(res => res.data);

// ————— Customer Controller —————
export const getAllCustomers = () =>
    apiClient.get('/api/customers').then(res => res.data?.data || res.data);

export const getCustomerById = (id) =>
    apiClient.get(`/api/customers/${id}`).then(res => res.data);

export const createCustomer = (payload) =>
    apiClient.post('/api/customers', payload).then(res => res.data);

export const updateCustomer = (id, payload) =>
    apiClient.put(`/api/customers/${id}`, payload).then(res => res.data);

export const deleteCustomer = (id) =>
    apiClient.delete(`/api/customers/${id}`).then(res => res.data);

// ————— Call Controller —————
export const getAllCalls = () =>
    apiClient.get('/api/calls').then(res => res.data?.data || res.data);

export const getCallById = (id) =>
    apiClient.get(`/api/calls/${id}`).then(res => res.data);

export const createCall = (payload) =>
    apiClient.post('/api/calls', payload).then(res => res.data);

export const updateCall = (id, payload) =>
    apiClient.put(`/api/calls/${id}`, payload).then(res => res.data);

export const deleteCall = (id) =>
    apiClient.delete(`/api/calls/${id}`).then(res => res.data);

// ————— Admin Controller —————
export const getAllAdmins = () =>
    apiClient.get('/api/admins').then(res => res.data?.data || res.data);

export const getAdminById = (id) =>
    apiClient.get(`/api/admins/${id}`).then(res => res.data);

export const createAdmin = (payload) =>
    apiClient.post('/api/admins', payload).then(res => res.data);

export const updateAdmin = (id, payload) =>
    apiClient.put(`/api/admins/${id}`, payload).then(res => res.data);

export const deleteAdmin = (id) =>
    apiClient.delete(`/api/admins/${id}`).then(res => res.data);

export const approveSecurity = (id) =>
    apiClient.post(`/api/operators/${id}/approve-security`).then(res => res.data);

// ————— Extra —————
export const getAllCities = () =>
    apiClient.get('/api/locations/cities').then(res => res.data?.data || res.data);

export const getDistrictsByCityId = (cityId) =>
    apiClient.get(`/api/locations/cities/${cityId}/districts`).then(res => res.data?.data || res.data);

export const getTownshipsByDistrictId = (districtId) =>
    apiClient.get(`/api/locations/districts/${districtId}/townships`).then(res => res.data?.data || res.data);

export const getNeighbourhoodsByTownshipId = (townshipId) =>
    apiClient.get(`/api/locations/townships/${townshipId}/neighbourhoods`).then(res => res.data?.data || res.data);

export const getCallDetails = (callId) =>
    apiClient.get(`/api/operator-customers/call/${callId}`)
        .then(res => {
            const data = res.data[0];
            return {
                serviceType: data.service_type,
                operatorId: data.operator_id,
                city: data.city,
            };
        });

export const getOperatorStatsByEmail = async (operatorEmail) => {
    try {
        const response = await axios.get(
            `/api/operator-customers/stats/by-email/${encodeURIComponent(operatorEmail)}`
        );
        return response.data;
    } catch (error) {
        console.error("Operatör istatistikleri alınamadı:", error);
        return { callCount: 0, customerCount: 0 };
    }
};

export const getAddressByNeighbourhood = async (neighbourhoodId) => {
    const response = await axios.post('/api/locations/addresses', { neighbourhoodId });
    return response.data;
};
// ————— Log Controller
export const fetchLogs = async (params = {}) => {
    try {
        // Query parametrelerini oluştur
        const queryParams = new URLSearchParams();

        if (params.operatorId) queryParams.append('operatorId', params.operatorId);
        if (params.startDate) queryParams.append('startDate', params.startDate);
        if (params.endDate) queryParams.append('endDate', params.endDate);
        if (params.search) queryParams.append('search', params.search);

        const response = await fetch(`/api/logs?${queryParams.toString()}`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Loglar alınırken hata:', error);
        throw error;
    }
};

