### Deploy application to the cluster

If the image is stored on GitHub Container Registry (GHCR) and the repository under which the image is published is private,
you have to create the secret for pulling images:

```
kubectl create secret docker-registry ghcr-secret -n parking --docker-server=ghcr.io --docker-username=$USERNAME --docker-password=$PASSWORD
```
where <code>USERNAME</code> and <code>PASSWORD
</code> are your credentials to log in to the registry.

The created secret is the value of <code>imagePullSecrets</code> section inside the yaml file of the deployment and is used
to authenticate the login to the registry.

#### Deploy the application to the cluster

- create the namespace to deploy the application in:
<code>kubectl create ns parking</code>
- deploy postgresql to the cluster:
<code>kubectl apply -f postgresql.yaml -n parking</code>
- deploy redis to the cluster:
<code>kubectl apply -f redis.yaml -n parking</code>
- deploy application to the cluster:
<code>kubectl apply -f parking.yaml -n parking</code>

> For the local environment you can set up the [minikube](https://minikube.sigs.k8s.io/docs/start/) cluster to deploy into.
