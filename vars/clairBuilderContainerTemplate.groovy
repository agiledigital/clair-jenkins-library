def call() {
	return [
		containerTemplate(
			name: 'clair-builder',
			image: 'agiledigital/clair-builder',
      alwaysPullImage: true,
			command: 'cat',
			ttyEnabled: true
		)
	]
}