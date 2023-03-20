### Deploy application to the cluster

If the parking image is in private container registry, you need to specify the <code>imagePullSecrets</code>
to authenticate the login to the registry.

If the image is stored on GitHub Container Registry (GHCR) and the repository under which the image is published,
you have to create the secret for pulling images:

<code>kubectl create secret docker-registry ghcr-secret -n parking --docker-server=ghcr.io --docker-username=$USERNAME --docker-password=$PASSWORD</code>,
where <code>USERNAME</code> and <code>PASSWORD</code> are your credentials to login to the registry.

- create the namespace to deploy the application in:
<code>kubectl create ns parking</code>
- deploy postgresql to the cluster
<code>kubectl apply -f postgresql.yaml -n parking</code>
- deploy redis to the cluster
<code>kubectl apply -f redis.yaml -n parking</code>
- deploy application to the cluster
<code>kubectl apply -f parking.yaml -n parking</code>
